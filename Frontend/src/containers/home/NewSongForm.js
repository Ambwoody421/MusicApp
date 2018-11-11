import React from 'react'
import {status, json, text} from '../../modules/functions'
import Config from '../../modules/config'
import InputWrapper from '../components/formFields/InputWrapper'
import ButtonWrapper from '../components/ButtonWrapper'

class NewSongForm extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            url: '',
            artist: '',
            queryArtistFlag: true,
            artistChoices: [],
            outcomeMessage: '',
            formErrors: {
                urlError: '',
                artistError: ''
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.addNewSong = this.addNewSong.bind(this);
        this.validateUrl = this.validateUrl.bind(this);
        this.handleArtistChange = this.handleArtistChange.bind(this);
        this.selectArtist = this.selectArtist.bind(this);

    }
    handleChange(e){
    
        this.setState({[e.target.id]: e.target.value});
    }
    selectArtist(name){
        this.setState({artist: name, artistChoices: []});
    }
    handleArtistChange(e){
        
        if(e.target.value.length === 1 && this.state.queryArtistFlag === true) {
            this.setState({[e.target.id]: e.target.value}, this.getArtistChoices);
        } else if(e.target.value.length > 1) {
            this.setState({[e.target.id]: e.target.value, queryArtistFlag: false});
        } else if(e.target.value.length === 0) {
            this.setState({[e.target.id]: e.target.value, queryArtistFlag: true, artistChoices: []});
        } else {
            this.setState({[e.target.id]: e.target.value});
        }
    }
    getArtistChoices() {
        fetch('http://'+Config.ip+':8080/song/getArtistsByLetter?letter=' + this.state.artist, {
            method: 'GET',
            credentials: 'include',
            headers: {'Accept': 'application/json'}
        }).then(status)
            .then(json)
            .then((d) => {
            
            this.setState({artistChoices: d});

        })
            .catch(error =>
                   this.setState({outcomeMessage: error.message}));
    }
    validateUrl(){
        const reqUrl = this.state.url.trim();
        const reqArtist = this.state.artist.trim();

        if(reqUrl === '' || reqArtist === ''){
            this.setState({outcomeMessage: 'Cannot leave fields blank.'});
            return;
        } else if (reqUrl.search(/https:\/\/(www.)?(m\.)?youtu\.?be(\.com)?\/(watch\?v=)?.+/g) === -1) {
            this.setState({outcomeMessage: 'Url is not acceptable.'});
            return;
        }
        
        this.addNewSong(reqUrl, reqArtist);
    }

    addNewSong(reqUrl, reqArtist){
        
        
        this.setState({outcomeMessage: 'Loading...'});
        
        var request = {url: reqUrl, artist: reqArtist};
        
        console.log(request);
        
        fetch('http://'+Config.ip+':8080/song/addNewSong', {
            method: 'POST',
            credentials: 'include',
            headers: {'Accept': 'text/plain', 'Content-Type': 'application/json'},
            body: JSON.stringify(request)
        }).then(status)
            .then(text)
            .then((d) => {
            this.setState({outcomeMessage: d, url: '', artist: '', artistChoices: [], queryArtistFlag: true});

        })
            .catch(error =>
                   this.setState({outcomeMessage: error.message}));
    }

    render() {
        let foundCount = 0;
        const arr = this.state.artistChoices.map((s, index) => {
        
            const regExp = new RegExp(this.state.artist, 'i');
            
            if(s.name.search(regExp) !== -1 && foundCount < 3){
                
                foundCount++;
                return (<input type='button' className='btn-sm btn-info' key={index} value={s.name} id='artist' onClick={() => this.selectArtist(s.name)} />);
            } else {
                return null;
            }
        });
    
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
                    handleChange={this.handleArtistChange} /> 
                {arr}
                &nbsp;
                <ButtonWrapper divClass='row justify-content-center' id='submit' val='Submit' click={this.validateUrl} class='btn btn-primary' />
                {this.state.outcomeMessage}
            </div>
        );
    }

}
export default NewSongForm;