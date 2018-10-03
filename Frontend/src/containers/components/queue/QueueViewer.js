import React from 'react'
import AddQueueSongPopup from './AddQueueSongPopup'
import QueuePlayer from './QueuePlayer'
import QueueSongRow from './QueueSongRow'
import Config from '../../../modules/config'
import {status, json} from '../../../modules/functions'

class QueueViewer extends React.Component{
    constructor(props){
    super(props);
    this.state = {
        showPlayer: false,
        currentSong: '',
        allSongs: []

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

                        var obj = {filename: s.filepath, songId: s.id};

                            return obj;
                        })

                        return array;

                    }).then((arr) => {
                        this.setState({allSongs: arr, currentSong: arr[0].filename});
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

        array.push(<QueueSongRow title={songsArray[i].filename} currentSong={this.state.currentSong} />);
     }

        return (
            <div style={{backgroundColor: 'lightGray', borderRadius: '5px'}} className='row'>
                <div className='col-lg-6'>
                    <div className='row'>
                        <AddQueueSongPopup id={this.props.id} />
                    </div>
                    <div>
                        {array}
                    </div>
                </div>
                <div className='offset-lg-1 col-lg-5'>
                    <div className='row'>
                        {this.state.showPlayer === true ? <button className='btn btn-success' name='showPlayer' onClick={this.PlayerView}>Close Queue</button> : <button className='btn btn-warning' name='showPlayer' onClick={this.PlayerView}>Play Queue</button>}
                    </div>
                    <div className='row'>
                        {this.state.showPlayer === true ? <QueuePlayer id={this.props.id} currentSong={this.state.currentSong} songEnded={this.songEnded} /> : null}
                    </div>
                </div>
            </div>
            );
        }

}
export default QueueViewer;