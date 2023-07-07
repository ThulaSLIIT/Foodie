import React from "react";
import Grid from "@mui/material/Grid";
import logo_light from "../assets/logo.png";

const Footer = () => {
    return(
        <div className={"footer"}>
            <Grid container spacing={2}>
                <Grid item xs={4}>
                    <div className={"footer-content footer-header"}>
                        <img src={logo_light} title="Jobs Lanka Light Image" alt="Jobs Lanka Light Image" width={300}/>
                    </div>
                </Grid>
                <Grid item xs={12}>
                    <div className={"footer-center"}>
                        Copyright © foodies.com 2023 All rights reserved.
                    </div>
                </Grid>
            </Grid>
        </div>
    );
}

export default Footer;
