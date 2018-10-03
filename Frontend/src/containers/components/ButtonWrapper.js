import React from 'react'

class ButtonWrapper extends React.Component {
    render() {
        return (
            <div className={this.props.divClass}>
                <p id={this.props.id} key={this.props.key} className={this.props.class} onClick={this.props.click}>{this.props.val}</p>
            </div>
            );
        }
}
export default ButtonWrapper;