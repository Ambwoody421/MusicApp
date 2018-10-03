import React from 'react'

class ErrorText extends React.Component {
    render() {
        return <p id={this.props.id} className='error'>{this.props.val}</p>;
        }
}
export default ErrorText;