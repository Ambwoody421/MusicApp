import React from 'react'

class ButtonWrapper extends React.Component {
    render() {
        return (
            <div className={this.props.divClass} style={this.props.style} >
                <input type='button' id={this.props.id} key={this.props.key} className={this.props.class} onClick={this.props.click} value={this.props.val} />
            </div>
            );
        }
}
export default ButtonWrapper;