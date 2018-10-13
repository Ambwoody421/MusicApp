import React from 'react'

class QueueSongRow extends React.Component{
    render() {

        const styleObj = {
            borderRadius: '50px',
            border: '7px double #000000',
            fontFamily: 'Georgia, serif',
            fontSize: '16px',
            paddingLeft: '2px',
            paddingRight: '2px',
            paddingTop: '5px',
            marginTop: '2px',
            boxShadow: '5px 5px 10px 0px rgba(0,0,0,0.75)'
            
        };


        return (
            <div style={styleObj} className={this.props.currentSong === this.props.title ? 'bg-light text-dark text-center col-xs-11' : 'bg-secondary text-white text-center col-xs-11'}>
            {this.props.title}
            </div>
        );
    }
}
export default QueueSongRow;