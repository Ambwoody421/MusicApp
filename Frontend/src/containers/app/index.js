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
            <main>
                <div className='container'>
                    <Route exact path="/" component={Home} />
                    <Route exact path="/myGroups" component={AllGroups} />
                    <Route exact path="/login" component={Login} />
                </div>
            </main>
            
                <MyNav />
        </div>
        );
    }
}

export default App;
