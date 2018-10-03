import React from 'react'

class OwnerRow extends React.Component{
    render() {
        return (
        <div>
        <div className='row groupOwner'>
            <div className='col-lg-4'>
                <h5>{this.props.name}</h5>
            </div>
            <div className='offset-lg-2 col-lg-4 offset-lg-4'>
                <h5>Owner</h5>
            </div>
        </div>
        <br />
        </div>
        );
    }

}
export default OwnerRow;