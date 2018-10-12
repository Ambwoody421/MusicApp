import React from 'react'

class QueueSongRow extends React.Component{
    render() {

        const styleObj = {
        borderRadius: '22px',
        fontFamily: 'Georgia, serif',
        fontSize: '18 px',
        padding: '5px'
        };

        
        return (
            <div style={styleObj} className={this.props.currentSong === this.props.title ? 'bg-light border border-info text-dark text-center' : 'bg-secondary border border-info text-white text-center'}>
                <p>{this.props.title}</p>
            </div>
        );
    }
}
export default QueueSongRow;