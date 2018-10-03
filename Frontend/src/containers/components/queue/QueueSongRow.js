import React from 'react'

class QueueSongRow extends React.Component{
    render() {

        const styleObj = {
        backgroundColor: '#028ea3',
        opacity: '0.7',
        borderRadius: '5px',
        color: '#050200',
        fontFamily: 'Oswald',
        padding: '8px'
        };

        if (this.props.currentSong === this.props.title) {
            styleObj.backgroundColor = '#e65202';
        }
        return (
            <div style={styleObj}>
                <h5>{this.props.title}</h5>
            </div>
        );
    }
}
export default QueueSongRow;