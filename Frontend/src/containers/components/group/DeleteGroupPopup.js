import React from "react";
import Modal from "react-modal";
import {status, text} from '../../../modules/functions'
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

class DeleteGroupPopup extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            groupId: '',
            groups: '',
            errorText: '',
            outcomeMessage: ''
        }
        this.deleteGroup = this.deleteGroup.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    openModal() {
        this.setState({modalIsOpen: true, outcomeMessage: ''});
    }

    afterOpenModal() {

        var options = this.props.data.map(function(t) {
            return (
                <option id={t.id} name={t.name} value={t.id} key={t.id}>{t.name}</option>
            );
        });

        this.setState({groups: options});
    }

    closeModal() {
        this.setState({modalIsOpen: false});
    }

    deleteGroup() {

        var obj = {id: this.state.groupId};
        const request = JSON.stringify(obj);

        fetch('http://'+Config.ip+':8080/group/deleteGroup', {
            method: 'POST',
            credentials: 'include',
            headers: {'Accept': 'text/plain', 'Content-Type': 'application/json'},
            body: request
        }).then(status)
            .then(text)
            .then(() => {
            this.setState({outcomeMessage: 'Success!'}, this.props.refresh);
            console.log("Success.");
        })
            .catch(error =>
                   this.setState({outcomeMessage: error.message}));
    }


    render() {
        return (
            <div>
                <button className='btn-sm btn-danger' style={{margin: '10px'}} onClick={this.openModal}>Delete Group</button>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onAfterOpen={this.afterOpenModal}
                    onRequestClose={this.closeModal}
                    style={customStyles}
                    contentLabel="Delete Group"
                    >
                    <h5>Delete a Group</h5>
                    <select name='groupId' className='form-control' value={this.state.groupId} onChange={this.handleChange}>
                        {this.state.groups}
                    </select>
                    <button 
                        className='btn-sm btn-danger' 
                        onClick={this.deleteGroup}>
                        Delete Group
                    </button>
                    {this.state.outcomeMessage}
                </Modal>
            </div>

        );
    }
}
export default DeleteGroupPopup;