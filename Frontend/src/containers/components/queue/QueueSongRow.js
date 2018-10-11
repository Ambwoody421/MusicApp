import React from 'react'

class QueueSongRow extends React.Component{
    render() {

        const styleObj = {
        borderRadius: '10px',
        fontFamily: 'Georgia, serif',
        fontSize: '5px',
        padding: '8px'
        };

        
        return (
            <div style={styleObj} className={this.props.currentSong === this.props.title ? 'bg-light border border-info text-dark' : 'bg-secondary border border-info text-white'}>
                <h5>{this.props.title}</h5>
            </div>
        );
    }
}
export default QueueSongRow;