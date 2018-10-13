import React from "react";
import Modal from "react-modal";
import {status, json} from '../../../modules/functions'
import Config from '../../../modules/config'

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
        this.getUsers = this.getUsers.bind(this);
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }
    
    openModal() {
        this.setState({modalIsOpen: true});
    }

    afterOpenModal() {
        // gather all users from database
        this.getUsers();
    }

    closeModal() {
        this.setState({modalIsOpen: false, outcomeMessage: ''});
    }

    
    getUsers(){
        fetch('http://'+Config.ip+':8080/user/seeAllUsers', {
            method: 'GET',
            credentials: 'include',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
        }).then(status)
            .then(json)
            .then(function(data) {

            var array = data.map(function(s) {

                return <option key={s.id + ':' + s.name} value={s.id}>{s.name}</option>;
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
            this.setState({outcomeMessage: 'Success!'}, this.props.refreshMembers);
            console.log("Success.");
        })
            .catch(error =>
                   this.setState({outcomeMessage: error.message}));
    }

    render() {
        return (
            <div className='col-xs-8 bg-dark text-white'>
                <button id={this.props.id + ':addMember'} className='btn-sm btn-warning' onClick={this.openModal}>Add Member </button>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onAfterOpen={this.afterOpenModal}
                    onRequestClose={this.closeModal}
                    style={customStyles}
                    contentLabel="Add Queue Song"
                    >
                    <h4>Add a User:</h4>
                    <div className='row justify-content-center'>
                        <select name='user' onChange={this.handleChange}>
                            {this.state.data}
                        </select>
                    </div>
                    <div className='row justify-content-center'>
                        <button className='btn-sm btn-success' onClick={this.addUser}>Add Member</button>
                    </div>
                    {this.state.outcomeMessage}
                </Modal>
            </div>
        );
    }
}
export default AddMemberPopup;