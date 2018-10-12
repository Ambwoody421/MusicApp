import React from 'react'
import GroupViewer from './GroupViewer'
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'
import CreateGroupPopup from './CreateGroupPopup'
import DeleteGroupPopup from './DeleteGroupPopup'

class AllGroups extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            data: []
        }

        this.setUpGroups = this.setUpGroups.bind(this);
        this.refreshGroups = this.refreshGroups.bind(this);
    }

    setUpGroups(){

        var groups = this.state.data.map(function(t) {
            return (
                <GroupViewer id={t.id} name={t.name} key={t.id} />
            );
        });

        return groups;
    }

    componentDidMount(){

        this.refreshGroups();


    }
    
    refreshGroups(){
        fetch('http://'+Config.ip+':8080/user/getOwnedGroups', {
            method: 'GET',
            credentials: 'include',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
        }).then(status)
            .then(json)
            .then((d) => {

            this.setState({data: d});

        })
            .catch(error =>
                   console.log(error.message));
    }

    render() {

        return (
            <div>
                <div>&nbsp;</div>
                <div className='row'>
                    <CreateGroupPopup />
                    <DeleteGroupPopup data={this.state.data} refresh={this.refreshGroups} />
                </div>
                <div>&nbsp;</div>
                <div className='col-xs-12'>
                    {this.setUpGroups()}
                </div>
            </div>
        );
    }

}
export default AllGroups;