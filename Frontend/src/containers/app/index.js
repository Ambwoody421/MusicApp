import React from 'react'
import { Route } from 'react-router-dom'
import Home from '../home/Home'
import Login from '../components/Login'
import NavBar from '../components/NavBar'
import AllGroups from '../components/group/AllGroups'

const App = () => (
  <div>
    <header>
      <NavBar obj={[{id: "Home", displayName: "Home", to: "/"}, {id: "My Groups", displayName: "My Groups", to: "/myGroups"}, {id: "Login", displayName:"Login", to:"/login"}]} />
    </header>

    <main>
        <div className='container'>
            <Route exact path="/" component={Home} />
            <Route exact path="/myGroups" component={AllGroups} />
            <Route exact path="/login" component={Login} />
        </div>
    </main>
  </div>
)

export default App
