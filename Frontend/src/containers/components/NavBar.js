import React from 'react'
import NavItem from './NavItem'

class NavBar extends React.Component{
    constructor(props){
    super(props);
    this.state = {
        selected: ''
    }
    this.handleClick = this.handleClick.bind(this);
    this.items = this.items.bind(this);

    }

    handleClick(e){

        this.setState({selected: e.target.id});
    }

    items(){
        let array = [];

        for(var i=0; i < this.props.obj.length; i++){

            let currentObj = this.props.obj[i];

        if(this.state.selected === currentObj.displayName){

            array.push(<NavItem id={currentObj.id} displayName={currentObj.displayName} active="Y" to={currentObj.to} handleClick={this.handleClick} /> );
        } else {

            array.push(<NavItem id={currentObj.id} displayName={currentObj.displayName} active="N" to={currentObj.to} handleClick={this.handleClick} /> );
        }

        }

        return array;
    }
    render() {
        return (
        <div className='offset-lg-5'>
            {this.items()}
        </div>
        );
    }

}
export default NavBar;