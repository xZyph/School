		</div>
		</div>
		<?php thinkup_sidebar_html(); ?>
	</div>
	</div>

	<footer>
		<?php thinkup_input_footerlayout();
		echo	'<!-- #footer -->';  ?>

		<div id="sub-footer">

		<div id="sub-footer-core">
		
			<div class="copyright">
			<?php plastic_copyright(); ?>
			</div>

			<?php if ( has_nav_menu( 'sub_footer_menu' ) ) : ?>
			<?php wp_nav_menu( array( 'depth' => 1, 'container_class' => 'sub-footer-links', 'container_id' => 'footer-menu', 'theme_location' => 'sub_footer_menu' ) ); ?>
			<?php endif; ?>

			<?php if ( ! has_nav_menu( 'sub_footer_menu' ) ) : ?>
			<?php  thinkup_input_socialmediafooter(); ?>
			<?php endif; ?>

		</div>
		</div>
	</footer>

</div>

<?php wp_footer(); ?>

</body>
</html>