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
        return (
        <div className='d-flex flex-sm-row groupMember'>
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