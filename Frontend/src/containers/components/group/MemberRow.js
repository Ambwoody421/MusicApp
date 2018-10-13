import React from 'react'
import ButtonWrapper from '../ButtonWrapper'

class MemberRow extends React.Component{
    render() {

        const styleObj = {
            borderRadius: '50px',
            border: '7px double #000000',
            fontFamily: 'Georgia, serif',
            fontSize: '16px',
            paddingLeft: '2px',
            paddingRight: '2px',
            paddingTop: '5px',
            marginTop: '2px',
            boxShadow: '5px 5px 10px 0px rgba(0,0,0,0.75)'

        };
        return (
            <div style={styleObj} className='row bg-info text-white'>
                <div className='col-xs-6'>
                    <h5>{this.props.name}</h5>
                </div>
                {this.props.remove === true ?
                    <ButtonWrapper 
                        id={this.props.id} 
                        divClass='offset-xs-2 col-xs-4'
                        class='btn btn-danger' 
                        click={this.props.removeAction} 
                        val={this.props.val} /> : null}
            </div>
        );
    }
}

export default MemberRow;