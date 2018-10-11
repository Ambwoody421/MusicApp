import React from 'react'

class ButtonWrapper extends React.Component {
    render() {
        return (
            <p id={this.props.id} name={this.props.name} className={this.props.class} onClick={this.props.click}>{this.props.val}</p>
        );
    }
}
export default ButtonWrapper;