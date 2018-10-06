import React from 'react'
import SongVideo from '../SongVideo'

class QueuePlayer extends React.Component{
    render() {
        return (
            <div className='row'>
                <SongVideo filename={this.props.currentSong} songEnded={this.props.songEnded} />
            </div>
            );
        }

}
export default QueuePlayer;