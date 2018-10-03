import React from 'react'
import SongVideo from '../SongVideo'

class QueueViewer extends React.Component{
    render() {
        return (
            <div className='row'>
                <SongVideo filename={this.props.currentSong} songEnded={this.props.songEnded} />
            </div>
            );
        }

}
export default QueueViewer;