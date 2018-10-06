import React from "react";
import Popup from "reactjs-popup";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'

class AddQueueSongPopup extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            artistData: null,
            selectedArtist: '',
            selectedSong: '',
            songData: null,
            outcomeMessage: ''
        }
        this.addSong = this.addSong.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.getAllArtist = this.getAllArtist.bind(this);
        this.getAllSongs = this.getAllSongs.bind(this);
    }

    componentDidMount(){
        this.getAllArtist();
        
    }

    handleChange(e) {
        if (e.target.id === 'selectedArtist') {
            this.setState({[e.target.id]: e.target.value}, this.getAllSongs);
        } else {
            this.setState({[e.target.id]: e.target.value});
        }
       
    }

    getAllArtist() {
        fetch('http://'+Config.ip+':8080/song/getAllArtists', {
            method: 'GET',
            credentials: 'include'
        }).then(status)
            .then(json)
            .then(function(data) {

            var array = data.map(function(s) {

                return <option value={s.id} id={s.name}>{s.name}</option>;
            });


            return array;

        }).then((arr) => {
            // initalize artist to blank
            arr.push(<option value={''}>{''}</option>);
            this.setState({artistData: arr});
        })
            .catch(error =>
                   alert(error.message));
    }
    
    getAllSongs() {
        fetch('http://'+Config.ip+':8080/song/getAllSongsByArtist?artist=' + this.state.selectedArtist, {
            method: 'GET',
            credentials: 'include',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
        }).then(status)
            .then(json)
            .then(function(data) {

            var array = data.map(function(s) {

                return <option value={s.id} id={s.title}>{s.title}</option>;
            });


            return array;

        }).then((arr) => {
            
            arr.push(<option value={''}>{''}</option>);
            this.setState({songData: arr});
        })
            .catch(error =>
                   alert(error.message));
    }
    
    addSong() {

        var obj = {groupId: this.props.id, songId: this.state.selectedSong};
        const request = JSON.stringify(obj);

        fetch('http://'+Config.ip+':8080/queue/addSong', {
                                        method: 'POST',
                                        credentials: 'include',
                                        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                                        body: request
                                    }).then(status)
                                        .then(json)
                                        .then(() => {
                                            this.setState({outcomeMessage: 'Success adding song: ' + this.state.selectedSongName});
                                            console.log("Success.");
                                        })
                                        .catch(error =>
                                            this.setState({outcomeMessage: error.message}));
    }

    render() {
    
        return (
            <Popup style={{height: '40%'}} className='col-xs-7' modal trigger={<button id={this.props.id + ':addSong'} className='btn-sm btn-warning'>Add Song</button>} position="right center">
                <div className='row justify-content-center'>
                    <h4>Add a Song:</h4>
                    Artist: <select id='selectedArtist' onChange={this.handleChange} value={this.state.selectedArtist}>
                                {this.state.artistData}
                            </select>
                    <button className='btn-sm btn-warning' onClick={this.addSong}>Add Song</button>
                    {this.state.outcomeMessage}
                </div>
                {this.state.selectedArtist === '' ? null : <select id='selectedSong' onChange={this.handleChange} value={this.state.selectedSong}>
                    {this.state.songData}</select>}
              </Popup>
        );
    }
}
export default AddQueueSongPopup;