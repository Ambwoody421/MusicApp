import React from 'react';
import {Navbar, Nav, NavItem, NavLink} from 'reactstrap';

class MyNav extends React.Component {
    
    
    render() {
        const loc = window.location.pathname;
        const style = {
            color: 'white',
            fontWeight: '10px'
        };
    
        return (
            <footer className='fixed-bottom' style={{backgroundColor: 'gray'}}>
                <Navbar expand='xs'>
                <Nav className='mr-auto' navbar navbar-expand-xs pills>
                    <NavItem>
                        <NavLink className='nav-link' href="/" active={loc === '/'} style={style}>Home</NavLink>
                    </NavItem>
                    <NavItem >
                        <NavLink className='nav-link' href="/ownerGroups" active={loc === '/ownerGroups'} style={style}>Groups</NavLink>
                    </NavItem>
                    <NavItem >
                        <NavLink className='nav-link' href="/myGroups" active={loc === '/myGroups'} style={style}>My Groups</NavLink>
                    </NavItem>
                    <NavItem >
                        <NavLink className='nav-link' href="/login" active={loc === '/login'} style={style}>Login</NavLink>
                    </NavItem>
                </Nav>
                </Navbar>
            </footer>
        );
    }
}
export default MyNav;