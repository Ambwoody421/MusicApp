import React from 'react'
import ButtonWrapper from '../ButtonWrapper'
import MemberViewer from './MemberViewer'
import PlaylistViewer from '../playlist/PlaylistViewer'
import QueueViewer from '../queue/QueueViewer'

class GroupViewer extends React.Component{
    constructor(props){
    super(props);
    this.state = {
        selected: ''
    }

    this.handleClick = this.handleClick.bind(this);
    this.showView = this.showView.bind(this);
    this.activateBtn = this.activateBtn.bind(this);
    }

    handleClick(e) {
        if(this.state.selected === e.target.id){
            this.setState({selected: ''});
        } else {
            this.setState({selected: e.target.id});
        }
    }


    showView() {

        const temp = this.state.selected;

        var view = temp.split(":");

        switch(view[1]){

        case 'ViewMembers':
            return <MemberViewer id={this.props.id} />;
        case 'Playlists':
            return <PlaylistViewer id={this.props.id} />;
        case 'Queue':
            return <QueueViewer id={this.props.id} />;
        default:
            break;

        }
    }

    activateBtn(button) {

        return (this.state.selected === button ? 'redBtn btn-lg wrapText' : 'btn-primary btn-lg wrapText');
    }

    render() {
        return (
        <div>
            <h3>{this.props.name}</h3>
            <div className='row'>
            <ButtonWrapper id={this.props.id + ":ViewMembers"} divClass='col-xs-1' class={this.activateBtn(this.props.id + ":ViewMembers")} val="View Members" click={this.handleClick} />
            <ButtonWrapper id={this.props.id + ":Playlists"} divClass='col-xs-1' class={this.activateBtn(this.props.id + ":Playlists")} val="Playlists" click={this.handleClick} />
            <ButtonWrapper id={this.props.id + ":Queue"} divClass='col-xs-1' class={this.activateBtn(this.props.id + ":Queue")} val="Queue" click={this.handleClick} />
            </div>
            {this.showView()}
        </div>
        );
    }

}
export default GroupViewer;