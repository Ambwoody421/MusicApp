import React from 'react'
import PlaylistRow from './PlaylistRow'
import CreatePlaylistPopup from './CreatePlaylistPopup'
import Config from '../../../modules/config'
import {status, json} from '../../../modules/functions'

class PlaylistViewer extends React.Component{
    constructor(props){
    super(props);
    this.state = {
        d: []
    }
}
    componentDidMount(){

            let obj = {id: this.props.id};

                    const request = JSON.stringify(obj);


                    fetch('http://'+Config.ip+':8080/group/getAllPlaylists', {
                                    method: 'POST',
                                    credentials: 'include',
                                    headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                                    body: request
                                }).then(status)
                                    .then(json)
                                    .then(function(data) {

                                        var array = data.map(function(s) {

                                                    return <PlaylistRow playlistId={s.id} name={s.name} addSong='Add Song' play='Play' />
                                                });


                                        return array;

                                    }).then((arr) => {
                                        this.setState({d: arr});
                                    })
                                    .catch(error =>
                                    alert(error.message));

        }
    render() {
        return (
            <div>
                <br />
                <CreatePlaylistPopup groupId={this.props.id} />
                <div className='row'><span>&nbsp;</span></div>
                <div>
                    {this.state.d}
                </div>
            </div>
            );
        }

}
export default PlaylistViewer;