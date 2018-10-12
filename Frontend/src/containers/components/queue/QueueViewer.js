import React from 'react'
import AddQueueSongPopup from './AddQueueSongPopup'
import QueuePlayer from './QueuePlayer'
import QueueSongRow from './QueueSongRow'
import Config from '../../../modules/config'
import {status, json} from '../../../modules/functions'
import ButtonWrapper from '../ButtonWrapper'

class QueueViewer extends React.Component{
    constructor(props){
    super(props);
    this.state = {
        showPlayer: false,
        currentSong: '',
        allSongs: [],
        refreshTimerId: null

    }

    this.PlayerView = this.PlayerView.bind(this);
    this.queueSongs = this.queueSongs.bind(this);
    this.deleteLastSong = this.deleteLastSong.bind(this);
    this.songEnded = this.songEnded.bind(this);

}
    PlayerView() {
        this.setState({showPlayer: !this.state.showPlayer});
    }

     songEnded() {
        this.deleteLastSong();
    }
    queueSongs() {

    console.log('Getting songs list');
    let obj = {id: this.props.id};

        const request = JSON.stringify(obj);


            fetch('http://'+Config.ip+':8080/group/getAllQueueSongs', {
                method: 'POST',
                credentials: 'include',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                body: request
                }).then(status)
                .then(json)
                .then((data) => {
                    
                        var array = data.map(function(s) {
                            var obj = {filepath: s.filepath, songId: s.id, title: s.title};
                                return obj;
                            })
                            return array;

                    }).then((arr) => {
                        // check if queue is empty. If empty, refresh queue every 15 seconds.
                        if(arr === null) {
                            const refreshTimerId = setInterval(this.queueSongs, 15000);
                            this.setState({allSongs: null, currentSong: null, refreshTimerId: refreshTimerId});
                            // clear refresh if queue is full again.
                        } else {
                            clearInterval(this.state.refreshTimerId);
                            this.setState({allSongs: arr, currentSong: arr[0], refreshTimerId: null});
                        }
                    })
                    .catch(error =>
                        alert(error.message));

        }

    deleteLastSong() {

    let obj = {groupId: this.props.id, songId: this.state.allSongs[0].songId};

                        const request = JSON.stringify(obj);

                                fetch('http://'+Config.ip+':8080/queue/deleteSong', {
                                                method: 'POST',
                                                credentials: 'include',
                                                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                                                body: request
                                            }).then(status)
                                                .then(json)
                                                .then((data) => {

                                                    console.log('Deleted ' + obj.songId + ' Successfully.');
                                                    this.queueSongs();
                                                })
                                                .catch(error =>
                                                alert(error.message));
    }

    componentDidMount(){

        this.queueSongs();

    }

    render() {

    var songsArray = this.state.allSongs;
     var array = [];
     for (var i = 0; i < songsArray.length; i++) {

        array.push(<QueueSongRow key={i + ':groupQueue' + this.props.id} title={songsArray[i].title} currentSong={this.state.currentSong.title} />);
     }

        return (
            <div style={{backgroundColor: 'lightGray', borderRadius: '5px'}} className='row'>
                <div className='offset-lg-1 col-lg-5'>
                    <ButtonWrapper 
                        divClass='row justify-content-center'
                        class='btn btn-success' 
                        name='showPlayer' 
                        click={this.PlayerView}
                        val={this.state.showPlayer === true ? 'Close' : 'Play Queue'} 
                        />
                    {this.state.showPlayer === true ? 
                        <QueuePlayer 
                            id={this.props.id} 
                            divClass='row justify-content-center' 
                            currentSong={this.state.currentSong} 
                            songEnded={this.songEnded} 
                            /> : null}
                </div>
                <div className='col-lg-6'>
                    <div className='row justify-content-center'>
                        <AddQueueSongPopup id={this.props.id} refreshSongs={this.queueSongs} />
                    </div>
                    <div>
                        {array}
                    </div>
                </div>
            </div>
            );
        }

}
export default QueueViewer;