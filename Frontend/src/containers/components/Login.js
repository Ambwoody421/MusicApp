import React from 'react'
import ButtonWrapper from './ButtonWrapper'
import InputWrapper from './formFields/InputWrapper'
import {status, json} from '../../modules/functions'
import Config from '../../modules/config'

class Login extends React.Component {
    constructor(props){
    super(props);
    this.state = {
    name: '',
    password: '',
    formErrors: {nameError: '', passwordError: ''}
    }
    this.handleChange = this.handleChange.bind(this);
    this.submit = this.submit.bind(this);
    this.validateForm = this.validateForm.bind(this);
    }

    handleChange(e) {
        this.setState({[e.target.id]: e.target.value });
    }

    submit() {
        let obj = {name: this.state.name, password: this.state.password};

        const request = JSON.stringify(obj);


        fetch('http://'+Config.ip+':8080/user/validateUser', {
                credentials: 'include',
                method: 'POST',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                body: request
            }).then(status)
                .then(json)
                .then(function(data) {
                    window.location.assign('http://'+Config.ip+':3000/')
                })
                .catch(error =>
                alert(error.message));

    }

    validateForm() {
    let errorCount = 0;
    let nameError = '';
    let passwordError = '';

    if(this.state.name.length === 0){
        nameError = 'Email is required';
        errorCount++;
    }
    if(this.state.password.length === 0){
        passwordError = 'Password is required';
        errorCount++;
    }

    this.setState({formErrors: {nameError: nameError, passwordError: passwordError}});

    if(errorCount === 0){
        this.submit();
    }

    }

    render() {
        return (
            <div className='jumbotron'>
                <h3 className='offset-lg-5'>Welcome!</h3>
                <div className='row'>
                    <InputWrapper id='name' divClass='col-lg-6 offset-lg-3' displayName='Email' type='text' class='form-control' val={this.state.name} errorText={this.state.formErrors.nameError} handleChange={this.handleChange} />
                </div>
                <div className='row'>
                    <InputWrapper id='password' divClass='col-lg-6 offset-lg-3' displayName='Password' type='password' class='form-control' val={this.state.password} errorText={this.state.formErrors.passwordError} handleChange={this.handleChange} />
                </div>
                <div className='row'>
                    <ButtonWrapper class='btn-primary btn-lg' divClass='offset-lg-5' val='Login' click={this.validateForm} />
                </div>
            </div>
            );
        }
}
export default Login;