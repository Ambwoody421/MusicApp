import React from 'react'
import ErrorText from './ErrorText'

class InputWrapper extends React.Component {
    render() {
        return (
        <div className={this.props.divClass} style={this.props.divStyle}>
            <h5>{this.props.displayName}</h5>
            <input id={this.props.id} type={this.props.type} className={this.props.class} value={this.props.val} onChange={this.props.handleChange} />
            <ErrorText val={this.props.errorText} />
        </div>
        );
        }
}
export default InputWrapper;