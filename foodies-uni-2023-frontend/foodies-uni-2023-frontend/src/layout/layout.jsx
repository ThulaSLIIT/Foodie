import React from "react";
import Box from "@mui/material/Box";
import Header from "./header";
import Footer from "./footer";

const Layout = ({children}) => {

    return(
        <Box>
            <Header/>
            <Box component="main" className={"main-section"} sx={{marginTop: '100px'}}>
                {children}
            </Box>
            <Footer/>
        </Box>
    )

}

export default Layout;
