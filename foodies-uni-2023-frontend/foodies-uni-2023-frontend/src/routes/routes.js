import React from "react";
import Login from "../pages/login/login";
import Profile from "../pages/profile/profile";
import PostManagement from "../pages/post/post-management";
import Post from "../pages/post/post";
import Wall from "../pages/wall/wall";

export const routes = [
    {
        path: "/",
        element: <Wall/>,
    },
    {
        path: "/login",
        element: <Login/>,
    },
    {
        path: "/profile",
        element: <Profile/>,
    },
    {
        path: "/post-management",
        element: <PostManagement/>,
    },
    {
        path: "/post",
        element: <Post/>,
    },
    {
        path: "/wall",
        element: <Wall/>,
    }
];
