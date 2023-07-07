import React, {useState} from "react";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Logo from '../../assets/logo.png';
import * as Api from "../../service/apis";
import swal from 'sweetalert';
import Cookies from 'js-cookie';
import {useNavigate} from "react-router-dom"

import './login.scss';

const Login = () => {

    const navigate = useNavigate();

    const [email, setEmail] = useState(null);
    const [password, setPassword] = useState(null);

    const login = () => {

        if(email) {

            const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if(regex.test(email)) {
                if(password) {

                    let body = {
                        email: email,
                        password: password
                    };

                    Api.login(body).then(r => {
                        if(r.success) {
                            console.log(r.data.body)
                            Cookies.set('foodies_user_id', r.data.body.id);
                            Cookies.set('foodies_user_email', r.data.body.email);
                            Cookies.set('foodies_user_firstName', r.data.body.firstName);
                            Cookies.set('foodies_user_LastName', r.data.body.lastName);
                            navigate(`/?id=${r.data.body.id}`);
                        } else {
                            swal("Sorry!", r.data.msg, "error");
                        }
                    })

                } else {
                    swal("Invalid Input", "Please enter valid password", "error");
                }
            } else {
                swal("Invalid Input", "Please enter valid email", "error");
            }
        } else {
            swal("Invalid Input", "Please enter valid email", "error");
        }
    }

    return(
        <section id={'login-bg'}>
            <div id={'login-box'}>
                <img src={Logo} title="foodies.com" alt="logo.png" width={200}/>
                <TextField
                    label="Email"
                    id="outlined-size-small"
                    size="small"
                    className={"text-fields"}
                    onChange={e => {setEmail(e.target.value)}}
                />
                <TextField
                    type="password"
                    label="Password"
                    id="outlined-size-small"
                    size="small"
                    className={"text-fields"}
                    onChange={e => {setPassword(e.target.value)}}
                />
                <Button variant="contained" onClick={login}>Login</Button>
            </div>
        </section>
    )
}

export default Login;
