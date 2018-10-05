import React from 'react'
import { render } from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import App from './containers/app'
import 'bootstrap/dist/css/bootstrap.min.css';

import 'sanitize.css/sanitize.css'
import './index.css'

const target = document.querySelector('#root')

render(
    <BrowserRouter>
        <App />
    </BrowserRouter>,
    target
)
