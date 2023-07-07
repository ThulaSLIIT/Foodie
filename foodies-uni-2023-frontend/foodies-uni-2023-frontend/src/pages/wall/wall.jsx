import React, {useEffect, useState} from "react";
import Layout from "../../layout/layout";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import User from "../../assets/user.png";
import {Carousel} from "react-responsive-carousel";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ChatBubbleIcon from "@mui/icons-material/ChatBubble";
import Button from "@mui/material/Button";
import Cookies from 'js-cookie';
import * as Api from "../../service/apis";
import './wall.scss';
import Toolbar from "@mui/material/Toolbar";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";
import swal from "sweetalert";


const x = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
const Wall = () => {

    const navigate = useNavigate();
    const searchParams = new URLSearchParams(document.location.search)

    const [userId, setUserId] = useState(0);
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        if (searchParams.get('id')) {
            setUserId(searchParams.get('id'));
        } else {
            if (!Cookies.get("foodies_user_id")) navigate("/login");
            setUserId(Number(Cookies.get("foodies_user_id")));
        }
    }, [])

    useEffect(() => {
        loadPosts();
    }, [userId])

    const loadPosts = () => {
        Api.getAllPosts().then(r => {
            if (r.success) {
                setPosts(r.data.body);
            } else {
                swal("Sorry!", r.data.msg, "error");
            }
        });
    }

    const like = (postId) => {
        Api.manageLikes(postId, userId).then(r => {
            if (r.success) {
                loadPosts();
            } else {
                swal("Sorry!", r.data.msg, "error");
            }
        });
    }

    return (
        <Layout>
            <section id={'wall-bg'}>

                <Box sx={{textAlign: 'center'}}>
                    <Button variant="contained" sx={{width: '56%', marginTop: '10px', color: 'white'}}
                            onClick={() => navigate("/post-management")}>Create a post</Button>
                </Box>

                {
                    posts.map(r =>
                        <Card sx={{maxWidth: 600}} className={'wall-card'}>
                            <CardHeader
                                avatar={
                                    <img src={User} title="user" alt="user.png" width={50}/>
                                }
                                title={r.user.firstName + " " + r.user.lastName}
                                subheader={new Date(r.date).toDateString()}
                            />

                            <Carousel>
                                {
                                    r.images.map(img => <CardMedia
                                        component="img"
                                        alt="green iguana"
                                        height="340"
                                        image={img}
                                    />)
                                }
                            </Carousel>

                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    {r.title}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {r.description}
                                </Typography>
                            </CardContent>
                            <CardActions disableSpacing>
                                <IconButton aria-label="add to favorites" onClick={() => like(r.id)}>
                                    <FavoriteIcon/>
                                    <span className={'react-counts'}>{r.likes}</span>
                                </IconButton>
                                <IconButton aria-label="add to favorites">
                                    <ChatBubbleIcon/>
                                    <span className={'react-counts'}
                                          onClick={() => navigate(`/post?id=${r.id}`)}>{r.comments}</span>
                                </IconButton>
                                <Button size="small" onClick={() => navigate(`/post?id=${r.id}`)}>View More</Button>
                            </CardActions>

                        </Card>
                    )
                }

            </section>
        </Layout>
    )

}

export default Wall;
