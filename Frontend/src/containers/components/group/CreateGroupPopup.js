import React from "react";
import Modal from "react-modal";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'
import InputWrapper from '../formFields/InputWrapper'

const customStyles = {
    content : {
        top                   : '50%',
        left                  : '50%',
        right                 : 'auto',
        bottom                : 'auto',
        marginRight           : '-50%',
        transform             : 'translate(-50%, -50%)'
    }
};

Modal.setAppElement('#root')

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
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    handleChange(e) {
        this.setState({groupName: e.target.value});
    }

    openModal() {
        this.setState({modalIsOpen: true});
    }

    afterOpenModal() {

    }

    closeModal() {
        this.setState({modalIsOpen: false, outcomeMessage: ''});
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
            this.setState({outcomeMessage: 'Success!'}, this.props.refresh);
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

        if(this.state.groupName.length > 20){
            errorText = 'Name cannot exceed 20 characters.';
            errorCount++;
        }

        this.setState({errorText: errorText});

        if(errorCount === 0){
            this.createGroup();
        }
    }

    render() {
        return (
            <div>
            <button className='btn-sm btn-success' style={{margin: '10px'}} onClick={this.openModal}>Create Group</button>
            <Modal
            isOpen={this.state.modalIsOpen}
            onAfterOpen={this.afterOpenModal}
            onRequestClose={this.closeModal}
            style={customStyles}
            contentLabel="Create Group"
            >
            <h5>Create a Group</h5>
            <InputWrapper 
            displayName='Group Name:' 
            type='text' 
            val={this.state.groupName} 
            handleChange={this.handleChange} 
            class='form-control' 
            errorText={this.state.errorText} />
            <button 
            className='btn-sm btn-success' 
            onClick={this.validateForm}>
            Create Group
            </button>
            {this.state.outcomeMessage}
            </Modal>
            </div>

        );
    }
}
export default CreateGroupPopup;