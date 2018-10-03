import React from 'react'
import ButtonWrapper from '../ButtonWrapper'
import {status, text} from '../../../modules/functions'
import Config from '../../../modules/config'

class MemberRow extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            status: ''
        }
        this.removeMember = this.removeMember.bind(this);
    }

    removeMember() {

        const obj = {groupId: this.props.groupId, userId: this.props.id};
        const request = JSON.stringify(obj);

         fetch('http://'+Config.ip+':8080/group/removeMember', {
                                        method: 'POST',
                                        credentials: 'include',
                                        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                                        body: request
                                    }).then(status)
                                        .then(text)
                                        .then(() => {
                                            this.setState({status: 'removed'});
                                        })
                                        .catch(error => {
                                            alert(error.message);
                                            this.setState({status: error.message})});
    }
    render() {
        return (
        <div>
        <div className='row groupMember'>
            <div className='col-lg-4'>
                <h5>{this.props.name}</h5>
            </div>
            <div className='offset-lg-2 col-lg-4 offset-lg-4'>
                <ButtonWrapper id={this.props.id} class='redBtn btn-primary' click={this.removeMember} val={this.props.val} />
                {this.state.status}
            </div>
        </div>
        <br />
        </div>
        );
    }

}
export default MemberRow;