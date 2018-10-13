import React from 'react'
import { Route} from 'react-router-dom'
import Home from '../home/Home'
import Login from '../components/Login'
import AllGroups from '../components/group/AllGroups'
import MyNav from '../components/navigation/MyNav'

class App extends React.Component {
    render() {
        return (
            <div className='container' style={{marginBottom: '70px'}}>
                    <div className='col-xs-11'>
                        <Route exact path="/" component={Home} />
                        <Route exact path="/memberGroups" render={() => <AllGroups type='member' />} />
                        <Route exact path="/myGroups" render={() => <AllGroups type='owner' />} />
                        <Route exact path="/login" component={Login} />
                    </div>

                <MyNav />
            </div>
        );
    }
}

export default App;
