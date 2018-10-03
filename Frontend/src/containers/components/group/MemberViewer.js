import React from 'react'
import {status, json} from '../../../modules/functions'
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
    this.addMember = this.addMember.bind(this);
    }

    addMember(){
    }

    componentDidMount(){

        let obj = {id: this.props.id};

                const request = JSON.stringify(obj);


                fetch('http://'+Config.ip+':8080/group/allMembers', {
                                method: 'POST',
                                credentials: 'include',
                                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                                body: request
                            }).then(status)
                                .then(json)
                                .then(function(data) {

                                    var array = data.map(function(s) {
                                                const type = s.userTypeId;

                                                return (type === 2 ? <MemberRow id={s.userId} groupId={obj.id} name={s.userName} type={s.userTypeId} val='Remove' /> : <OwnerRow id={s.userId} name={s.userName} type={s.userTypeId} />)
                                            });


                                    return array;

                                }).then((arr) => {
                                    this.setState({d: arr});
                                })
                                .catch(error =>
                                alert(error.message));

    }


    render() {

        return (
        <div className='jumbotron'>
            <div id='test' />
            {this.state.d}
            <AddMemberPopup id={this.props.id} />
        </div>
        );
    }

}
export default MemberViewer;