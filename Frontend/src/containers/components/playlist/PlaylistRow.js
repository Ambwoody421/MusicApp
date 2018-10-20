import React from 'react'
import ButtonWrapper from '../ButtonWrapper'

class PlaylistRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            status: ''
        }
    }


    render() {

        const styleObj = {
            borderRadius: '50px',
            border: '7px double #000000',
            fontFamily: 'Georgia, serif',
            fontSize: '16px',
            paddingLeft: '2px',
            paddingRight: '2px',
            paddingTop: '5px',
            marginTop: '4px',
            boxShadow: '5px 5px 10px 0px rgba(0,0,0,0.75)'

        };
        return (
            <div style={styleObj} className='d-flex flex-sm-row groupMember'>
                <div className='p-2'>
                    <h5>{this.props.name}</h5>
                </div>
                <ButtonWrapper divClass='p-2' id={this.props.playlistId+':AddSong'} class='redBtn btn-primary' click={() => console.log("add")} val={this.props.addSong} />
                <ButtonWrapper divClass='p-2' id={this.props.playlistId+':Play'} class='redBtn btn-primary' click={() => console.log("play")} val={this.props.play} />
                {this.state.status}
            </div>
        );
    }

}
export default PlaylistRow;