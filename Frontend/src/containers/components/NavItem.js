import React from 'react'
import { Link } from 'react-router-dom'

class NavItem extends React.Component{
    constructor(props){
    super(props);

    this.checkActive = this.checkActive.bind(this);

    }

    checkActive(){

        return this.props.active === 'Y' ? 'btn active' : 'btn';
    }
    render() {
        return (
            <Link className="navigationLink" to={this.props.to}><button type="button" id={this.props.id} className={this.checkActive()} onClick={this.props.handleClick}>{this.props.displayName}</button></Link>
        );
    }

}
export default NavItem;