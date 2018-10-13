import React from 'react'
import MemberViewer from './MemberViewer'
import PlaylistViewer from '../playlist/PlaylistViewer'
import QueueViewer from '../queue/QueueViewer'
import GroupCommands from './GroupCommands'

class GroupViewer extends React.Component{
    constructor(props){
    super(props);
    this.state = {
        selected: ''
    }

    this.handleClick = this.handleClick.bind(this);
    this.showView = this.showView.bind(this);
    }

    handleClick(e) {
        if(this.state.selected === e.target.id){
            this.setState({selected: ''});
        } else {
            this.setState({selected: e.target.id});
        }
    }


    showView() {
        const type = this.props.type;
        const temp = this.state.selected;

        var view = temp.split(":");

        switch(view[1]){

        case 'ViewMembers':
            return <MemberViewer id={this.props.id} type={type} />;
        case 'Playlists':
            return <PlaylistViewer id={this.props.id} type={type} />;
        case 'Queue':
            return <QueueViewer id={this.props.id} type={type} />;
        default:
            break;

        }
    }

    render() {
        return (
        <div className='bg-dark text-white' style={{padding: '16px', borderRadius: '7px', marginBottom: '10px'}}>
            <h3>{this.props.name}</h3>
            <GroupCommands buttons={['ViewMembers', 'Playlists', 'Queue']} id={this.props.id} handleClick={this.handleClick} selected={this.state.selected} />
            {this.showView()}
        </div>
        );
    }

}
export default GroupViewer;