import React from 'react';
import Config from '../../modules/config';
import {status} from '../../modules/functions';

class SongVideo extends React.Component {
    constructor(props){
    super(props);
    this.state = {
        blob: null
    }
    this.downloadAudio = this.downloadAudio.bind(this);
    }

    downloadAudio() {
        
        const request = {filepath: this.props.filename};
        
        fetch('http://'+Config.ip+':8080/song/getSongAudio', {
            method: 'POST',
            credentials: 'include',
            headers: {'Content-Type': 'application/json', 'Accept': 'application/octet-stream'},
            body: JSON.stringify(request)
        }).then(status)
            .then(function(response) {
            return response.blob();
        })
            .then((data) => {
            var file = new Blob([data], {type: 'audio/mpeg'});
            var url = window.URL.createObjectURL(file);
            this.setState({blob: url});
        
        })
            .catch(error =>
                   alert(error.message));

    }
    
    
    componentDidMount() {

        this.downloadAudio();
    }
    componentDidUpdate(prevProps) {
        if (this.props.filename !== prevProps.filename) {
            this.downloadAudio();
        }
    }

    render() {
        return (
        <div>
            <h6>Now Playing: {this.props.filename}</h6>
            <audio id='audio' src={this.state.blob} autoPlay controls onEnded={this.props.songEnded} ></audio>
        </div>
        );
    }
}
export default SongVideo;