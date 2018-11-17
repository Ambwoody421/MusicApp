import React from 'react';
import {status, text} from '../../../modules/functions';
import Config from '../../../modules/config.json'

class Admin extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            status: ''
        }
        this.shudown = this.shutdown.bind(this);
    }
    shutdown() {
        fetch('http://'+Config.ip+':8080/admin/shutdown', {
            method: 'POST',
            credentials: 'include',
            headers: {'Accept': 'text/plain', 'Content-Type': 'text/plain'},
            body: null
        }).then(status)
            .then(text)
            .then((d) => {

            this.setState({status: d});

        })
            .catch(error =>
                   alert(error.message));
    }

    render() {
        return (
            <div className='row justify-content-center'>
                <input type='button' className='btn-lg btn-danger' value='Shutdown' onClick={this.shutdown} />
                {this.state.status}
            </div>
        );
    }
}
export default Admin;