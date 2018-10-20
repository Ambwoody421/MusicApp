import React from "react";
import Modal from "react-modal";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'
import InputWrapper from '../formFields/InputWrapper'

const customStyles = {
    content : {
        top                   : '50%',
        left                  : '50%',
        right                 : 'auto',
        bottom                : 'auto',
        marginRight           : '-50%',
        transform             : 'translate(-50%, -50%)'
    }
};

Modal.setAppElement('#root')

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
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    openModal() {
        this.setState({modalIsOpen: true});
    }

    afterOpenModal() {

    }

    closeModal() {
        this.setState({modalIsOpen: false, outcomeMessage: ''});
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
            this.setState({outcomeMessage: 'Success!'}, this.props.refresh);
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
        if(this.state.playlistName.length > 30){
            errorText = 'Name cant be 30 characters long.';
            errorCount++;
        }

        this.setState({errorText: errorText});

        if(errorCount === 0){
            this.createPlaylist();
        }
    }

    render() {
        return (
            <div>
                <button id={'createPlaylistButton'} className='btn btn-success' onClick={this.openModal}>Create New Playlist</button>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onAfterOpen={this.afterOpenModal}
                    onRequestClose={this.closeModal}
                    style={customStyles}
                    contentLabel="Create Playlist"
                    >
                    <InputWrapper 
                        displayName='Playlist Name:' 
                        type='text' 
                        val={this.state.playlistName} 
                        handleChange={this.handleChange} 
                        class='form-control' 
                        errorText={this.state.errorText} />
                    <button className='btn btn-success' onClick={this.validateForm}>Create Playlist</button>
                    {this.state.outcomeMessage}
                </Modal>
            </div>
        );
    }

}
export default CreatePlaylistPopup;