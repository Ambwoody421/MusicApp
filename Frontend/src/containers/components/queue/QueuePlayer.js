import React from 'react'
import SongVideo from '../SongVideo'

class QueuePlayer extends React.Component{
    render() {
        return (
            <div className={this.props.divClass}>
            {typeof this.props.currentSong === 'undefined' ? null : 
            <SongVideo 
                filename={this.props.currentSong.filepath} 
                displaySong={this.props.currentSong.title} 
                songEnded={this.props.songEnded} 
            />}
            </div>
            );
        }

}
export default QueuePlayer;