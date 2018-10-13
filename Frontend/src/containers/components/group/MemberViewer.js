import React from 'react'
import {status, text, json} from '../../../modules/functions'
import Config from '../../../modules/config'
import MemberRow from './MemberRow'
import OwnerRow from './OwnerRow'
import AddMemberPopup from './AddMemberPopup'

class MemberViewer extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            d: []
        }
        this.getMembers = this.getMembers.bind(this);
        this.removeMember = this.removeMember.bind(this);
    }

    getMembers(){

        let obj = {id: this.props.id};

        const request = JSON.stringify(obj);
        const checkType = this.props.type;

        fetch('http://'+Config.ip+':8080/group/allMembers', {
            method: 'POST',
            credentials: 'include',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
            body: request
        }).then(status)
            .then(json)
            .then((data) =>  {
            var array = data.map((s) => {

                const type = s.userTypeId;
                return (type === 2 ? <MemberRow key={s.userId + ':' + s.userName} id={s.userId} groupId={obj.id} name={s.userName} removeAction={this.removeMember} remove={checkType === 'owner'} type={s.userTypeId} val='Remove' /> : <OwnerRow key={s.userId + ':' + s.userName} id={s.userId} name={s.userName} type={s.userTypeId} />);


            });

            this.setState({d: array});

        })
            .catch(error =>
                   alert(error.message));

    }
    removeMember(e) {

        const obj = {groupId: this.props.id, userId: e.target.id};
        const request = JSON.stringify(obj);

        fetch('http://'+Config.ip+':8080/group/removeMember', {
            method: 'POST',
            credentials: 'include',
            headers: {'Content-Type': 'application/json'},
            body: request
        }).then(status)
            .then(text)
            .then(() => {
            this.getMembers();
        })
            .catch(error => {
            alert(error.message);
        })
    }

    componentDidMount(){

        this.getMembers();
    }


    render() {

        return (
            <div className='bg-dark text-white'>
                {this.state.d}
                {this.props.type === 'owner' ? <AddMemberPopup id={this.props.id} refreshMembers={this.getMembers} /> : null}
            </div>
        );
    }

}
export default MemberViewer;