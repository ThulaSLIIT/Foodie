import React, {useEffect, useState} from 'react';
import User from '../../assets/user.png';

import './profile.scss';
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import {Carousel} from "react-responsive-carousel";
import Layout from "../../layout/layout";
import FavoriteIcon from '@mui/icons-material/Favorite';
import "react-responsive-carousel/lib/styles/carousel.min.css";
import IconButton from "@mui/material/IconButton";
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import {useNavigate} from "react-router-dom";
import Cookies from 'js-cookie';
import * as Api from "../../service/apis";
import swal from "sweetalert";

const x = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

const Profile = () => {

    const navigate = useNavigate();
    const searchParams = new URLSearchParams(document.location.search)

    const [userId, setUserId] = useState(null);
    const [posts, setPosts] = useState([]);

    useEffect(()=>{
        if(searchParams.get('id')) {
            setUserId(searchParams.get('id'));
        } else {
            if(!Cookies.get("foodies_user_id")) navigate("/login");
            setUserId(Number(Cookies.get("foodies_user_id")));
        }
    }, [])

    useEffect(()=>{
        loadPosts();
    }, [userId])

    const loadPosts = () => {
        Api.getPostsByUserId(userId).then(r => {
            if(r.success) {
                setPosts(r.data.body);
            } else {
                swal("Sorry!", r.data.msg, "error");
            }
        });
    }

    const like = (postId) => {
        Api.manageLikes(postId, userId).then(r => {
            if(r.success) {
                loadPosts();
            } else {
                swal("Sorry!", r.data.msg, "error");
            }
        });
    }

    return(
        <Layout>
            <section id={'profile-bg'}>
                <div id={'profile-details'}>
                    <img src={User}title="user" alt="user.png" width={100}/>
                    <h2>{Cookies.get("foodies_user_firstName") + " " + Cookies.get("foodies_user_LastName")}</h2>
                </div>

                <div id={'profile-post-btn'}>
                    <Button variant="contained" onClick={()=>navigate("/post-management")}>Create a post</Button>
                </div>

                <div id={'profile-post-grid'}>
                    <Grid container spacing={2}>

                        {
                            posts.map(r =>
                                <Grid item xs={12} sm={6} md={6} lg={3} xl={3} xxl={3}>
                                    <Card sx={{ maxWidth: 300, margin: 'auto' }}>
                                        <Carousel>
                                            {
                                                r.images.map(img => <CardMedia
                                                    component="img"
                                                    alt="green iguana"
                                                    height="180"
                                                    image={img}
                                                />)
                                            }
                                        </Carousel>
                                        <CardContent>
                                            <Typography gutterBottom variant="h5" component="div">
                                                {r.title}
                                            </Typography>
                                            <Typography variant="body2" color="text.secondary">
                                                {r.description.slice(0, 100) + "..."}
                                            </Typography>
                                        </CardContent>
                                        <CardActions>
                                            <IconButton aria-label="add to favorites">
                                                <FavoriteIcon />
                                                <span className={'react-counts'}>{r.likes}</span>
                                            </IconButton>
                                            <IconButton aria-label="add to favorites">
                                                <ChatBubbleIcon />
                                                <span className={'react-counts'}>{r.comments}</span>
                                            </IconButton>
                                            <Button size="small" onClick={()=>navigate(`/post?id=${r.id}`)}>View More</Button>
                                        </CardActions>
                                    </Card>
                                </Grid>
                            )
                        }


                    </Grid>
                </div>

            </section>
        </Layout>
    )

}

export default Profile;
