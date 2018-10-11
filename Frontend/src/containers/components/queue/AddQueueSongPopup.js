import React from "react";
import Modal from "react-modal";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'

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

class AddQueueSongPopup extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            artistData: null,
            selectedArtist: '',
            selectedSong: '',
            selectedSongName: '',
            songData: null,
            outcomeMessage: '',
            modalIsOpen: false
        }
        this.addSong = this.addSong.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.getAllArtist = this.getAllArtist.bind(this);
        this.getAllSongs = this.getAllSongs.bind(this);
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    openModal() {
        this.setState({modalIsOpen: true});
    }

    afterOpenModal() {
        // gather all artists from database
        this.getAllArtist();
    }

    closeModal() {
        this.setState({modalIsOpen: false});
    }

    
    handleChange(e) {
        if (e.target.id === 'selectedArtist') {
            this.setState({[e.target.id]: e.target.value}, this.getAllSongs);
        } else if(e.target.id === 'selectedSong') {
            const temp = document.querySelector('#selectedSong');
            const chosenSong = temp[temp.selectedIndex].id;
            console.log(chosenSong);
            this.setState({[e.target.id]: e.target.value, selectedSongName: chosenSong});
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

                return <option key={s.id + ':' + s.name} value={s.id} id={s.name}>{s.name}</option>;
            });


            return array;

        }).then((arr) => {
            // initalize artist to blank
            arr.push(<option key={'blankArtist'} value={''}>{''}</option>);
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

                return <option key={s.id + ':' + s.title} value={s.id} id={s.title}>{s.title}</option>;
            });


            return array;

        }).then((arr) => {
            
            arr.push(<option key={'blankSong'} value={''}>{''}</option>);
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
                                            this.setState({outcomeMessage: 'Success adding song: ' + this.state.selectedSongName, selectedArtist: '', selectedSong: '', selectedSongName: ''}, this.props.refreshSongs);
                                            console.log("Success.");
                                        })
                                        .catch(error =>
                                            this.setState({outcomeMessage: error.message}));
    }

    render() {
    
        return (
            <div className='col-xs-8 bg-dark text-white'>
                <button className='btn-sm btn-primary' onClick={this.openModal}>Add Song</button>
                <Modal
                isOpen={this.state.modalIsOpen}
                onAfterOpen={this.afterOpenModal}
                onRequestClose={this.closeModal}
                style={customStyles}
                contentLabel="Add Queue Song"
                >
                    <h5>Add a Song</h5>
                    <div className='row justify-content-center'>
                        Artist: <select 
                                id='selectedArtist' 
                                onChange={this.handleChange} 
                                value={this.state.selectedArtist}>
                                {this.state.artistData}
                                </select>
                    </div>
                    {this.state.selectedArtist === '' ? null : 
                        <div className='row justify-content-center'>
                        Song: <select 
                                id='selectedSong' 
                                onChange={this.handleChange} 
                                value={this.state.selectedSong}>
                                {this.state.songData}
                            </select>
                        </div>}
                    {this.state.selectedSong === '' ? null : 
                        <div className='row justify-content-center'>
                        <button 
                            className='btn-sm btn-warning' 
                            onClick={this.addSong}>
                            Add Song
                        </button>
                        </div>}
                    <div className='row justify-content-center'>
                        {this.state.outcomeMessage}
                    </div>
                </Modal>
            </div>
        );
    }
}
export default AddQueueSongPopup;