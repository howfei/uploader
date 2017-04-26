/**
 * 
 */
package org.howsun.wrs.service.impl;

import org.howsun.wrs.domain.Comment;
import org.howsun.wrs.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午8:25:15
 */
@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<Comment> implements CommentService{

}
