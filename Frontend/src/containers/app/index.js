import React from 'react'
import { Route} from 'react-router-dom'
import Home from '../home/Home'
import Login from '../components/Login'
import AllGroups from '../components/group/AllGroups'
import MyNav from '../components/navigation/MyNav'
import Admin from '../components/admin/Admin'

class App extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            user: ''
        };
    }

    componentDidMount(){
        const x = document.cookie;
        const user = x.split('=')[1];
        this.setState({user: user});
    }
    render() {
        return (
            <div className='container' style={{marginBottom: '70px'}}>
                <div className='col-xs-11'>
                    <Route exact path="/" component={Home} />
                    <Route exact path="/memberGroups" render={() => <AllGroups type='member' />} />
                    <Route exact path="/myGroups" render={() => <AllGroups type='owner' />} />
                    <Route exact path="/login" component={Login} />
                    {this.state.user === '52' ? <Route exact path="/admin" component={Admin} /> : null}
                </div>

                <MyNav user={this.state.user} />
            </div>
        );
    }
}

export default App;
