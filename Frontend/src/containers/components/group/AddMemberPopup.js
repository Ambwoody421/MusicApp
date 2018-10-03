import React from "react";
import Popup from "reactjs-popup";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'

class AddMemberPopup extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            data: [],
            selectedUser: '',
            outcomeMessage: ''
        }
        this.addUser = this.addUser.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount(){

        fetch('http://'+Config.ip+':8080/user/seeAllUsers', {
                                    method: 'GET',
                                    credentials: 'include',
                                    headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
                                }).then(status)
                                    .then(json)
                                    .then(function(data) {

                                        var array = data.map(function(s) {

                                                    return <option value={s.id}>{s.name}</option>;
                                                });


                                        return array;

                                    }).then((arr) => {
                                      this.setState({data: arr});
                                    })
                                    .catch(error =>
                                    alert(error.message));
    }

    handleChange(e) {
        console.log(e.target.value);
        this.setState({selectedUser: e.target.value});
    }

    addUser() {

        var obj = {groupId: this.props.id, userId: this.state.selectedUser, userTypeId: '2'};
        const request = JSON.stringify(obj);

        fetch('http://'+Config.ip+':8080/group/addMember', {
                                        method: 'POST',
                                        credentials: 'include',
                                        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                                        body: request
                                    }).then(status)
                                        .then(json)
                                        .then(() => {
                                            this.setState({outcomeMessage: 'Success!'});
                                            console.log("Success.");
                                        })
                                        .catch(error =>
                                            this.setState({outcomeMessage: error.message}));
    }

    render() {
        return (
            <Popup className='col-lg-6' modal trigger={<button id={this.props.id + ':addMember'} className='greenBtn btn-primary'>Add Member </button>} position="right center">
                <div>
                    <h4>Add a User:</h4>
                    <select name='user' onChange={this.handleChange}>
                        {this.state.data}
                    </select>
                    <button className='greenBtn btn-primary' onClick={this.addUser}>Add Member</button>
                    {this.state.outcomeMessage}
                </div>
              </Popup>
        );
    }
}
export default AddMemberPopup;