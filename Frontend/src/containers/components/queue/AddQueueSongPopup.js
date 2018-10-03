import React from "react";
import Popup from "reactjs-popup";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'

class AddQueueSongPopup extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            data: [],
            selectedSong: null,
            selectedSongName: null,
            outcomeMessage: ''
        }
        this.addSong = this.addSong.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount(){

        fetch('http://'+Config.ip+':8080/song/getAllSongs', {
                                    method: 'GET',
                                    credentials: 'include',
                                    headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
                                }).then(status)
                                    .then(json)
                                    .then(function(data) {

                                        var array = data.map(function(s) {

                                                    return <option value={s.id} id={s.title}>{s.title + ' by ' + s.artist}</option>;
                                                });


                                        return array;

                                    }).then((arr) => {
                                      this.setState({data: arr});
                                    })
                                    .catch(error =>
                                    alert(error.message));
    }

    handleChange(e) {
        this.setState({selectedSong: e.target.value, selectedSongName: e.target.id});
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
            <Popup className='col-lg-6' modal trigger={<button id={this.props.id + ':addSong'} className='greenBtn btn-primary'>Add Song</button>} position="right center">
                <div>
                    <h4>Add a Song:</h4>
                    <select name='song' onChange={this.handleChange}>
                        {this.state.data}
                    </select>
                    <button className='greenBtn btn-primary' onClick={this.addSong}>Add Song</button>
                    {this.state.outcomeMessage}
                </div>
              </Popup>
        );
    }
}
export default AddQueueSongPopup;