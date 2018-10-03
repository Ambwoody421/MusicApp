import React from 'react'

class ButtonWrapper extends React.Component {
    render() {
        return (
            <p id={this.props.id} className={this.props.class} onClick={this.props.click}>{this.props.val}</p>
        );
    }
}
export default ButtonWrapper;