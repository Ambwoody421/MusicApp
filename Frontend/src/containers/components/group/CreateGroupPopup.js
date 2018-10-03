import React from "react";
import Popup from "reactjs-popup";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'
import InputWrapper from '../formFields/InputWrapper'

class CreateGroupPopup extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            groupName: '',
            errorText: '',
            outcomeMessage: ''
        }
        this.createGroup = this.createGroup.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.validateForm = this.validateForm.bind(this);
    }

    handleChange(e) {
        this.setState({groupName: e.target.value});
    }

    createGroup() {

        var obj = {name: this.state.groupName};
        const request = JSON.stringify(obj);

        fetch('http://'+Config.ip+':8080/group/createGroup', {
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

     validateForm() {
        let errorCount = 0;
        let errorText = '';

        if(this.state.groupName.length < 3){
            errorText = 'Name must be at least 3 characters long.';
            errorCount++;
        }

        this.setState({errorText: errorText});

        if(errorCount === 0){
            this.createGroup();
        }
    }

    render() {
        return (
            <Popup className='col-lg-6' modal trigger={<button id={'createGroupButton'} className='greenBtn btn-primary'>Create New Group</button>} position="right center">
                <div>
                    <InputWrapper displayName='Group Name:' type='text' val={this.state.groupName} handleChange={this.handleChange} class='form-control' errorText={this.state.errorText} />
                    <button className='greenBtn btn-primary' onClick={this.validateForm}>Create Group</button>
                    {this.state.outcomeMessage}
                </div>
              </Popup>
        );
    }
}
export default CreateGroupPopup;