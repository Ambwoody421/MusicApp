import React from 'react'
import {status, json} from '../../modules/functions'
import Config from '../../modules/config'
import InputWrapper from '../components/formFields/InputWrapper'
import ButtonWrapper from '../components/ButtonWrapper'

class NewSongForm extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            url: '',
            artist: '',
            outcomeMessage: '',
            formErrors: {
                urlError: '',
                artistError: ''
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.addNewSong = this.addNewSong.bind(this);

    }
    handleChange(e){
        this.setState({[e.target.id]: e.target.value});
    }

    addNewSong(){
        this.setState({outcomeMessage: 'Loading...'});
        
        var request = {url: this.state.url, artist: this.state.artist};
        
        console.log(request);
        
        fetch('http://'+Config.ip+':8080/song/addNewSong', {
            method: 'POST',
            credentials: 'include',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
            body: JSON.stringify(request)
        }).then(status)
            .then(json)
            .then((d) => {
            var message = 'Song ' + d.title + ' was successfully created.'
            this.setState({outcomeMessage: message});

        })
            .catch(error =>
                   this.setState({outcomeMessage: error.message}));
    }

    render() {
        return (
            <div className='offset-lg-2 col-lg-8 jumbotron'>
                <h2>Add a New Song</h2>
                &nbsp;
                <InputWrapper 
                    id='url'  
                    displayName='Paste Youtube URL' 
                    type='text' 
                    class='form-control' 
                    val={this.state.url} 
                    errorText={this.state.formErrors.urlError} 
                    handleChange={this.handleChange} /> 
                &nbsp;
                <InputWrapper 
                    id='artist' 
                    displayName='Artist' 
                    type='text' 
                    class='form-control' 
                    val={this.state.artist} 
                    errorText={this.state.formErrors.artistError} 
                    handleChange={this.handleChange} /> 
                &nbsp;
                <ButtonWrapper divClass='row justify-content-center' id='submit' val='Submit' click={this.addNewSong} class='btn btn-primary' />
                {this.state.outcomeMessage}
            </div>
        );
    }

}
export default NewSongForm;