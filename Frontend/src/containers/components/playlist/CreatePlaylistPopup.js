import React from "react";
import Popup from "reactjs-popup";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'
import InputWrapper from '../formFields/InputWrapper'

class CreatePlaylistPopup extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            playlistName: '',
            errorText: '',
            outcomeMessage: ''
        }
        this.createPlaylist = this.createPlaylist.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.validateForm = this.validateForm.bind(this);
    }

    handleChange(e) {
        this.setState({playlistName: e.target.value});
    }

    createPlaylist() {

        var obj = {groupId: this.props.groupId, name: this.state.playlistName};
        const request = JSON.stringify(obj);

        fetch('http://'+Config.ip+':8080/playlist/createPlaylist', {
                                        method: 'POST',
                                        credentials: 'include',
                                        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                                        body: request
                                    }).then(status)
                                        .then(json)
                                        .then(() => {
                                            this.setState({outcomeMessage: 'Success!'});
                                            console.log("Success.");
                                        })
                                        .catch(error =>
                                            this.setState({outcomeMessage: error.message}));
    }

     validateForm() {
        let errorCount = 0;
        let errorText = '';

        if(this.state.playlistName.length < 3){
            errorText = 'Name must be at least 3 characters long.';
            errorCount++;
        }

        this.setState({errorText: errorText});

        if(errorCount === 0){
            this.createPlaylist();
        }
    }

    render() {
        return (
            <Popup className='col-lg-6' modal trigger={<button id={'createPlaylistButton'} className='greenBtn btn-primary'>Create New Playlist</button>} position="right center">
                <div>
                    <InputWrapper displayName='Playlist Name:' type='text' val={this.state.playlistName} handleChange={this.handleChange} class='form-control' errorText={this.state.errorText} />
                    <button className='greenBtn btn-primary' onClick={this.validateForm}>Create Playlist</button>
                    {this.state.outcomeMessage}
                </div>
              </Popup>
        );
    }
}
export default CreatePlaylistPopup;